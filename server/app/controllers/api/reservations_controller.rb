class Api::ReservationsController < ApplicationController
  include ActionController
  REALM = ""
  respond_to :xml, :json
  skip_before_action :verify_authenticity_token
  before_action :authenticate
  
  def index
    respond_with(@authenticated_user.reservations)
  end

  def show
    respond_with(@authenticated_user.reservations.find(params[:id]))
  end

  def create
    params = reservation_params
    reservation = Reservation.where("spot_id = ? and user_id = ?", params[:spot_id],  @authenticated_user[:id]).first

    if not reservation.nil?
      if reservation.update(params.merge(:status => Spot::STATUS[reservation_params["status"]]))
        respond_with(reservation, :location => "/")
      else
        respond_with({:error => reservation.errors}, :location => "/")
      end
    else
      reservation = Reservation.new(params.merge(
        :user => @authenticated_user,
        :status => Spot::STATUS[reservation_params["status"]]
      ))
      if reservation.save
        respond_with(reservation, :location => "/")
      else
        respond_with({:error => reservation.errors}, :location => "/")
      end
    end
  end

  def update
    reservation = @authenticated_user.reservations.find(params[:id])
    if reservation.update(reservation_params.merge(
        :status => Spot::STATUS[reservation_params["status"]]
        ))
      render(:json => reservation)
    else
      render(:json => {:error => reservation.errors})
    end
  end
  
  def destroy
    reservation = @authenticated_user.reservations.find(params[:id])
    if reservation.destroy 
      render(:json => {:deleted => true})
    else
      render(:json => {:error => reservation.errors})
    end
  end

  private
    def reservation_params
      params.permit(:spot_id, :status)
    end
    def authenticate
      authenticate_or_request_with_http_digest(REALM) do |email|
        if (user = User.find_by_email(email)) && user.is_mobile_user?
          @authenticated_user = user
          user.password
        end
      end
    end
end

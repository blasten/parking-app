class Api::LotsController < ApplicationController
  include ActionController
  respond_to :xml, :json
  REALM = ""
  skip_before_filter :verify_authenticity_token
  before_action :authenticate_admin_user, only: [:create, :update, :destroy]

  def index
    respond_with(Lot.all)
  end

  def show
    respond_with(Lot.find(params[:id]))
  end

  def create
    lot = Lot.new(lot_params)
    if lot.save
      render(:json => lot)
    else
      render(:json => {:error => lot.errors})
    end
  end

  def update
    lot = Lot.find(params[:id])
    if lot.update(lot_params)
      render(:json => lot)
    else
      render(:json => {:error => lot.errors})
    end
  end

  def destroy
    lot = Lot.find(params[:id])
    if lot.destroy
      render(:json => {:deleted => true})
    else
      render(:json => {:error => lot.errors})
    end
  end

  private
    def lot_params
      params.permit(:name, :coords, :enabled, :max_wait)
    end
    def authenticate_admin_user
      authenticate_or_request_with_http_digest(REALM) do |email|
        if (user = User.find_by_email(email)) && user.is_admin_user?
          @authenticated_user = user
          user.password
        end
      end
    end
end

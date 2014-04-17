class Api::UsersController < ApplicationController
  include ActionController
  REALM = ""
  respond_to :xml, :json
  skip_before_filter :verify_authenticity_token
  before_action :authenticate, only: [:index, :update, :update_me, :show, :show_me]

  def index
    respond_with(@authenticated_user.to_hash)
  end

  def show_me
    index
  end

  def show
    if @authenticated_user.id.to_s == params[:id]
      index
    else
      respond_with({:error => "Invalid User ID"})
    end
  end

  def create
    user = User.new(user_params)
    if user.save
      respond_with(user.to_hash, :location => "/")
    else
      respond_with({:error => user.errors}, :location => "/")
    end
  end

  def update
    user = @authenticated_user
    if user.id.to_s == params[:id]
      update_me
    else
      render(:json => {:error => "Invalid User ID"})
    end
  end

  def update_me
    user = @authenticated_user
    if user.update(user_params)
      render(:json => user.to_hash)
    else
      render(:json => {:error => user.errors})
    end
  end

  private
    def user_params
      params.permit(:name, :lastname, :email, :password)
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
class Api::UsersController < ApplicationController
  include ActionController
  REALM = ""
  respond_to :xml, :json
  skip_before_filter :verify_authenticity_token
  before_action :authenticate, only: [:index, :update, :me]

  def index
    respond_with @user_from_digest_auth.to_hash
  end

  def me
    index
  end

  def show
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
  end


  private

    def user_params
      params.permit(:name, :lastname, :email, :password)
    end

    def authenticate
      authenticate_or_request_with_http_digest(REALM) do |email|
        if user = User.find_by_email(email)
          @user_from_digest_auth = user
          user.password
        else
          nil
        end
      end
    end
end
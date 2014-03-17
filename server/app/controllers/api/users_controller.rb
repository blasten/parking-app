class Api::UsersController < ApplicationController
  include ActionController
  USERS = { "lifo" => "world" }
  before_action :authenticate, only: [:index, :update, :destroy]

  def index
    @message = @user_from_digest_auth

    respond_to do |format|
      format.json { render json: @message }
      format.xml { render xml: @message }
    end
  end

  def show
  end

  def create
  end

  def update
  end

  def destroy
  end

  private
    def authenticate
      authenticate_or_request_with_http_digest do |email|
        if user = User.find_by_email(email)
          @user_from_digest_auth = user
          user.password
        else
          nil
        end
      end
    end
end
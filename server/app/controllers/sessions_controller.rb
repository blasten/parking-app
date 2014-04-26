class SessionsController < ApplicationController
  def new
  end

  def show
    render 'new'
  end

  def create
    if user = User.find_admin_user(params[:session][:email], params[:session][:password])
      sign_in_user user
      redirect_to root_path
    else
      flash.now[:error] = 'Invalid email or password'
      render 'new'
    end
  end

  def destroy
    sign_out_user
    redirect_to root_path
  end
end

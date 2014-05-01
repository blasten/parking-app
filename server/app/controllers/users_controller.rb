class UsersController < ApplicationController
  before_filter :not_signed_in_user

  def index
    @users = User.all
  end

  def new
    @user = User.new
  end

  def create
    @user = User.new(user_params)
    User.admin_permission true
    result = @user.save(user_params)
    User.admin_permission false
    
    if result
       redirect_to user_path(@user), notice: "User created!"
    else
      render 'new'
    end
  end

  def edit
    @user = User.find(params[:id])
  end

  def update
    @user = User.find(params[:id])
    User.admin_permission true
    result = @user.update(user_params)
    User.admin_permission false

    if result
      redirect_to user_path(@user), notice: "Account settings successfully changed!"
    else
      render 'edit'
    end
  end

  def show
    @user = User.find(params[:id])
  end

  def edit_me
    @user = current_user
    render 'edit'
  end

  def destroy
    @user = User.find(params[:id])
    if @user.destroy
      redirect_to users_path, notice: "User deleted!"
    end
  end

  private
    def not_signed_in_user
      redirect_to sign_in_path unless signed_in?
    end

    def user_params
      params.require(:user).permit(:name, :lastname, :email, :password, :role)
    end
end

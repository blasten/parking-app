module SessionsHelper
  def sign_in_user(user) 
    # Create a permanent cookie (experies in 20 years from the creation)
    cookies.permanent[:remember_token] = user.remember_token

    # Makes current_user accesible in both controllers and views
    self.current_user = user
  end

  # Sets current_user
  def current_user=(user)
    @current_user = user
  end

  # Gets current_user
  # When the user navigates to a second page, we need to load `current_user`

  # if the value hasn't been loaded yet, it will attempt 
  # to load the user session using `remember_token`

  def current_user
    @current_user ||= User.where('remember_token = ? and role = ?', cookies[:remember_token], User::ROLE_ADMIN).first
  end

  # Returns true if the user's session is loaded
  def signed_in?
    !current_user.nil?
  end

  # Cleans `current_user` and destroys the cookie `:remember_token`
  def sign_out_user
    self.current_user = nil
    cookies.delete(:remember_token)
  end
end
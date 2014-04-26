class ReservationsController < ApplicationController
  before_filter :not_signed_in_user

  def update
  end

  def edit
  end

  def destroy
  end

  def index
    @reservations = Reservation.all
  end

  def show
  end

  private
    def not_signed_in_user
      redirect_to sign_in_path unless signed_in?
    end
end

class LotsController < ApplicationController
  before_filter :not_signed_in_user

  def index
    @lots = Lot.all
  end

  def new
    @lot = Lot.new
    @lot.latitude = Lot::DEFAULT_LATITUDE
    @lot.longitude = Lot::DEFAULT_LONGITUDE
  end

  def create
    @lot = Lot.new(lot_params)
    if @lot.save
       redirect_to lot_path(@lot), notice: "Lot created!"
    else
      render 'new'
    end
  end

  def update
    @lot = Lot.find(params[:id])
    if @lot.update(lot_params) 
      redirect_to lot_path(@lot), notice: "Lot updated!"
    else
      render 'edit'
    end
  end

  def edit
    @lot = Lot.find(params[:id])
  end

  def destroy
    @lot = Lot.find(params[:id])
    if @lot.destroy
      redirect_to lots_path, notice: "Lot removed!"
    else
      redirect_to lots_path
    end
  end

  def show
    @lot = Lot.find(params[:id])
    @show_status = params[:status] || 0
    @spots = (@show_status.to_i == 0) ? @lot.spots : Spot.where('lot_id = ? and status = ?', params[:id], params[:status]);
    @total_spots = Spot.count(:all, :conditions => ['lot_id = ?', params[:id]])
    @available_spots = 100 * 
    Spot.count(:all, :conditions => ['lot_id = ? and status = ?', params[:id], Spot::STATUS['AVAILABLE']]) / @total_spots

  end

  private
    def not_signed_in_user
      redirect_to sign_in_path unless signed_in?
    end

    def lot_params
      params.require(:lot).permit(:name, :enabled, :latitude, :longitude, :designation)
    end
end

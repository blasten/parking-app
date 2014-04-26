class SpotsController < ApplicationController
  def new
    @spot = Spot.new(lot_id: params[:lot_id])
    @spot.latitude = @spot.lot.latitude
    @spot.longitude = @spot.lot.longitude
  end

  def create
    @spot = Spot.new(spot_params.merge(:lot_id => params[:lot_id]))
    if @spot.save
      redirect_to lot_path(@spot.lot), notice: "Spot created!"
    else
      render 'new'
    end
  end

  def edit
    @spot = Spot.find(params[:id])
  end

  def update
    @spot = Spot.find(params[:id])
    if @spot.update(spot_params) 
      redirect_to lot_path(@spot.lot), notice: "Spot updated!"
    else
      render 'edit'
    end
  end

  def destroy
    @spot = Spot.find(params[:id])
    if @spot.destroy
      redirect_to lot_path(@spot.lot), notice: "Spot removed!"
    else
      redirect_to lot_path(@spot.lot)
    end
  end

  private
    def spot_params
      params.require(:spot).permit(:number, :status, :latitude, :longitude)
    end
end

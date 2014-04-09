class Api::LotsController < ApplicationController
  respond_to :xml, :json
 
  def index
    respond_with(Lot.all)
  end

  def show
    respond_with(Lot.find(params[:id]))
  end

  def create
    lot = Lot.new(lot_params)
    if lot.save
      respond_with(lot, :location => "/")
    else
      respond_with({:error => lot.errors}, :location => "/")
    end
  end

  def update
    lot = Lot.find(params[:id])
    if lot.update(lot_params)
      respond_with(lot, :location => "/")
    else
      respond_with({:error => lot.errors}, :location => "/")
    end
  end

  private
    def lot_params
      params.permit(:name, :coords, :enabled, :max_wait)
    end
end

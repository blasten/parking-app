class Api::LotsController < ApplicationController
  respond_to :xml, :json
 
  def index
    respond_with(Lot.all)
  end

  def show
    respond_with(Lot.find(params[:id]))
  end
end

class Api::BaseController < ApplicationController
  def index
    @message = { error: "Invalid request" }

    respond_to do |format|
      format.json { render json: @message }
      format.xml { render xml: @message }
    end
  end
end

class ReportsController < ApplicationController
  def parking_lot
    respond_to do |format|
      format.csv { send_data Lot.to_csv }
    end
  end

  def user_usage
    @from = params[:from] || Date.today;
    @to =  params[:to] || Date.today;
  end

  def user_usage_download
    @from = params[:from] || Date.today;
    @to =  params[:to] || Date.today;
    respond_to do |format|
      format.csv { send_data Report.to_csv(@from, @to) }
    end
  end
end

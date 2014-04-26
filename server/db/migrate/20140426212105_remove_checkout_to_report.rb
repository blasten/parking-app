class RemoveCheckoutToReport < ActiveRecord::Migration
  def change
    remove_column :reports, :checkin_time, :string
    remove_column :reports, :check_out_time, :string
  end
end

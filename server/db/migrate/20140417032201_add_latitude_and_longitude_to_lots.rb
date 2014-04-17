class AddLatitudeAndLongitudeToLots < ActiveRecord::Migration
  def change
    add_column :lots, :latitude, :decimal
    add_column :lots, :longitude, :decimal
  end
end

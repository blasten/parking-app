class CreateSpots < ActiveRecord::Migration
  def change
    create_table :spots do |t|
      t.decimal :latitude
      t.decimal :longitude
      t.integer :status
      t.references :lot

      t.timestamps
    end
  end
end

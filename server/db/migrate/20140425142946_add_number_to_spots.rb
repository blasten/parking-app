class AddNumberToSpots < ActiveRecord::Migration
  def change
    add_column :spots, :number, :string
  end
end

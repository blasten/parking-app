class RemoveCoordsFromLots < ActiveRecord::Migration
  def change
    remove_column :lots, :coords, :string
  end
end

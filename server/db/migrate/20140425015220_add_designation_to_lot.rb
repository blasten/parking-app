class AddDesignationToLot < ActiveRecord::Migration
  def change
    add_column :lots, :designation, :integer
  end
end

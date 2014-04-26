class AddTimeToReport < ActiveRecord::Migration
  def change
    add_column :reports, :time, :integer
  end
end

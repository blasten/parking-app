class CreateLots < ActiveRecord::Migration
  def change
    create_table :lots do |t|
      t.string :name
      t.string :coords
      t.boolean :enabled
      t.integer :max_wait

      t.timestamps
    end
  end
end

class CreateReservations < ActiveRecord::Migration
  def change
    create_table :reservations do |t|
      t.references :user
      t.references :spot
      t.integer :status

      t.timestamps
    end
  end
end

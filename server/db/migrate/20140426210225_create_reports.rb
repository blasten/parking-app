class CreateReports < ActiveRecord::Migration
  def change
    create_table :reports do |t|
      t.references :user, index: true
      t.references :lot, index: true
      t.datetime :checkin_time
      t.datetime :check_out_time

      t.timestamps
    end
  end
end

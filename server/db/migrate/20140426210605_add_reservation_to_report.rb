class AddReservationToReport < ActiveRecord::Migration
  def change
    add_reference :reports, :reservation, index: true
  end
end

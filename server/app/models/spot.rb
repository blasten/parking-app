class Spot < ActiveRecord::Base
  validate :verify_lot
  validate :verify_status

  before_destroy :remove_reservations
  before_save :config_reservations

  # Spot Status
  STATUS = {
    "AVAILABLE" => 1,
    "RESERVED" =>2,
    "OCCUPIED" => 3,
    "UNAVAILABLE" => 4
  }

  # Max number of spots within a region
  MAX_SPOT_WITHIN_REGION = 100

  belongs_to :lot

  def status
    self.class.spot_status(self.attributes["status"])
  end

  def is_available
    return self.attributes["status"] == STATUS["AVAILABLE"]
  end

  def is_reserved
    return self.attributes["status"] == STATUS["RESERVED"]
  end

  def is_occupied
    return self.attributes["status"] == STATUS["OCCUPIED"]
  end

  def is_unavailable
    return self.attributes["status"] == STATUS["UNAVAILABLE"]
  end

  def self.within(lat1, long1, lat2, long2)
    spots = Spot.where("latitude >= ? and longitude >= ? and latitude <= ? and longitude <= ?", 
        lat1.to_f,
        long1.to_f,
        lat2.to_f,
        long2.to_f)
    .take(MAX_SPOT_WITHIN_REGION)

    spots.map { |i| {
      id: i.id,
      lot_id: i.lot_id,
      status: i.status,
      latitude: i.latitude,
      longitude: i.longitude
      }
    }
  end

  def self.spot_status(status)
    STATUS.invert[status]
  end

  private
    def verify_lot
      if (not lot_id.present?) || Lot.find(lot_id).nil?
        errors.add(:lot_id, "is invalid")
      end
    end

    def verify_status
      if status != "AVAILABLE" && status != "UNAVAILABLE"
        errors.add(:status, "is invalid")
      end
    end

    def config_reservations
      if self.id.present? && status == "UNAVAILABLE"
        Reservation.destroy_all(:spot_id => self.id)
      end
    end

    def remove_reservations
      Reservation.find_by_spot_id(self.attributes["spot_id"]).destroy
    end
end
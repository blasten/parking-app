class Reservation < ActiveRecord::Base

  belongs_to :user

  validate :verify_status 
  validate :verify_spot
  before_save :update_spot
  before_destroy :restore_spot
  validates_uniqueness_of :user_id, :scope => :spot_id

  def status
    Spot::spot_status(self.attributes["status"])
  end
  
  private
    def verify_status
      if (not status.present?) || (status != "RESERVED" && status != "OCCUPIED")
        errors.add(:status)
      end
    end

    def verify_spot
      spot = Spot.find(spot_id)
      if spot.nil?
        errors.add(:spot_id, "is required")
      elsif status == "RESERVED"
        if !spot.is_available
          errors.add(:spot_id, "is not available")
        end
      elsif status == "OCCUPIED"
        if !spot.is_reserved && !spot.is_available
          errors.add(:spot_id, "is not reserved")
        end
      end
    end

    def update_spot
      spot = Spot.find(spot_id)
      if status == "RESERVED"
        spot.update(:status => Spot::STATUS["RESERVED"])
      elsif status == "OCCUPIED"
        spot.update(:status => Spot::STATUS["OCCUPIED"])
      end
    end

    def restore_spot
      Spot.find(spot_id).update(:status => Spot::STATUS["AVAILABLE"])
    end
end

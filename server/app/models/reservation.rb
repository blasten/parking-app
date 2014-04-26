class Reservation < ActiveRecord::Base
  belongs_to :user
  belongs_to :spot

  has_one :report

  validate :verify_status 
  validate :verify_lot_designation
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

    def verify_lot_designation
      lot = self.spot.lot
      if lot.designation == Lot::DESIGNATION_FACULTY
        if not self.user.is_staff?
          errors.add(:lot_id, "is reserved for staff")
        end
      elsif lot.designation == Lot::DESIGNATION_STUDENTS
        if not self.user.is_student?
          errors.add(:lot_id, "is reserved for students")
        end
      elsif lot.designation == Lot::DESIGNATION_VISITORS
        if not self.user.is_visitor?
          errors.add(:lot_id, "is reserved for visitors")
        end
      end
    end

    def update_spot
      spot = Spot.find(spot_id)
      if status == "RESERVED"
        spot.update_attribute(:status, Spot::STATUS["RESERVED"])
      elsif status == "OCCUPIED"
        Report.create(:reservation_id => self.id, :user => self.user, :lot => spot.lot) if self.report.nil?
        spot.update_attribute(:status, Spot::STATUS["OCCUPIED"])
      end
    end

    def restore_spot
      self.report.update(:time => Time.now - self.report.created_at) unless self.report.nil?

      if self.spot.status != Spot::STATUS["UNAVAILABLE"] && self.spot.lot.enabled == true
        self.spot.update_column(:status, Spot::STATUS["AVAILABLE"])
      end
    end
end

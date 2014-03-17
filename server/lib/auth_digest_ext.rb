module ActionController
  module HttpAuthentication
    module Digest
      def authentication_request_with_customization(controller, realm, message=nil)
        message = ({error: "Invalid access"}).to_json()
        authentication_request_without_customization(controller, realm, message)
      end
      alias_method_chain :authentication_request, :customization
    end
  end
end
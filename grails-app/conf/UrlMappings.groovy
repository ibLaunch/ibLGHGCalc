import org.springframework.security.access.AccessDeniedException
import org.springframework.security.acls.model.NotFoundException

class UrlMappings {
	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
                //"/reports/$id/$fileName" (controller:"/displayFile")
                "/reports" (controller:"emissionsSummary", action:"serveReportFile")
                "/*.pdf" (controller:"emissionsSummary", action:"serveReportFile")
		//  "/"(view:"/index")
                "/"(view:"/stationaryCombustion.gsp")
                //"/"(view:"/login/auth.gsp")
		"500"(view:'/error')
                "/login/$action?"(controller: "login")
                "/logout/$action?"(controller: "logout")
                /*
                "/EF_StationaryCombustion_EPA"(controller:"EF_StationaryCombustion_EPA", parseRequest:true){
                        action = [POST:"uploadEmissionFactorFile"]
                }
                */
                "/fileUpload"(controller:"fileUpload", parseRequest:true){
                        action = [POST:"uploadFile"]
                }

                "403"(controller: "errors", action: "error403")
                "500"(controller: "errors", action: "error500")
                "500"(controller: "errors", action: "error403", exception: AccessDeniedException)
                "500"(controller: "errors", action: "error403", exception: NotFoundException)
	}
}

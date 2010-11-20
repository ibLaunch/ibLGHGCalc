class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')

        "/EF_StationaryCombustion_EPA"(controller:"EF_StationaryCombustion_EPA", parseRequest:true){
                action = [POST:"uploadEmissionFactorFile"]
        }

	}
}

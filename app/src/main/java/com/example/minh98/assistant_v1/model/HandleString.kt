package com.example.minh98.assistant_v1.model

/**
 * Created by minh98 on 18/09/2017.
 */
class HandleString {
	companion object {
		fun convertToKoDau(from:String):String{
			var to=from
			to = to.replace(Regex("""(à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ)"""),"a")
			to = to.replace(Regex("""(è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ)"""),"e")
			to = to.replace(Regex("""(ì|í|ị|ỉ|ĩ)"""),"i")
			to = to.replace(Regex("""(ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ)"""),"o")
			to = to.replace(Regex("""(ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ)"""),"u")
			to = to.replace(Regex("""(ỳ|ý|ỵ|ỷ|ỹ)"""),"y")
			to = to.replace(Regex("""(đ)"""),"d")
			to = to.replace(Regex("""(À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ)"""),"A")
			to = to.replace(Regex("""(È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ)"""),"E")
			to = to.replace(Regex("""(Ì|Í|Ị|Ỉ|Ĩ)"""),"I")
			to = to.replace(Regex("""(Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ)"""),"O")
			to = to.replace(Regex("""(Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ)"""),"U")
			to = to.replace(Regex("""(Ỳ|Ý|Ỵ|Ỷ|Ỹ)"""),"Y")
			to = to.replace(Regex("""(Đ)"""),"D")
			return to
		}
	}
}
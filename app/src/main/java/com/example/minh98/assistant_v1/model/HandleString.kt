package com.example.minh98.assistant_v1.model

/**
 * Created by minh98 on 18/09/2017.
 */
class HandleString {
	companion object {
		fun convertToKoDau(from:String):String{
			var to=from
			to = to.replace(Regex("""([àáạảãâầấậẩẫăằắặẳẵ])"""),"a")
			to = to.replace(Regex("""([èéẹẻẽêềếệểễ])"""),"e")
			to = to.replace(Regex("""([ìíịỉĩ])"""),"i")
			to = to.replace(Regex("""([òóọỏõôồốộổỗơờớợởỡ])"""),"o")
			to = to.replace(Regex("""([ùúụủũưừứựửữ])"""),"u")
			to = to.replace(Regex("""([ỳýỵỷỹ])"""),"y")
			to = to.replace(Regex("""(đ)"""),"d")
			to = to.replace(Regex("""([ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ])"""),"A")
			to = to.replace(Regex("""([ÈÉẸẺẼÊỀẾỆỂỄ])"""),"E")
			to = to.replace(Regex("""([ÌÍỊỈĨ])"""),"I")
			to = to.replace(Regex("""([ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ])"""),"O")
			to = to.replace(Regex("""([ÙÚỤỦŨƯỪỨỰỬỮ])"""),"U")
			to = to.replace(Regex("""([ỲÝỴỶỸ])"""),"Y")
			to = to.replace(Regex("""(Đ)"""),"D")
			return to
		}
	}
}
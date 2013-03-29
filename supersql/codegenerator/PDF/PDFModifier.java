package supersql.codegenerator.PDF;

import java.util.List;

public class PDFModifier {

	Object le2_tmp;

	List le2_set;

	int limit, page_limit;

	boolean separate;

	String page_align, page_valign;

	int linewidth = 1;

	String linecolor;

	//String background;

	int width;

	int height;

	String align;

	String valign;

	String imagepath;

	//int image_width;
	//int image_height;
	float image_width;

	float image_height;

	float scale_width;

	float scale_height;

	int fontsize, char_all;

	String bgcolor;

	String fontcolor;

	public void clean1() {
		limit = 0;
		page_limit = 0;
		separate = false;
		page_align = "null";
		page_valign = "null";
		//writer.page_align = "center";
		//writer.page_valign = "top";
		//linewidth = 1;
		//writer.linewidth = 1;
		//writer.line = true;
		linecolor = "null";
		//writer.line_rgb = false;
	}

	public void page_clean(PDFEnv pdf_env) {
		//pdf_env.page_align = "left";
		//pdf_env.page_valign = "top";
		//pdf_env.linewidth = 1;
		//pdf_env.line = true;
		//pdf_env.line_rgb = false;
		//backgrond = "null";
	}

	//変数を初期化する
	public void clean2() {
		width = 0;
		height = 0;
		align = "left";
		valign = "center";
		imagepath = "null";
		image_width = 0;
		image_height = 0;
		scale_width = 0;
		scale_height = 0;
		fontsize = 8;
		bgcolor = "null";
		fontcolor = "null";
	}

	/*
	 * public void get_modifier1(Object o_modi){ le2_tmp =
	 * ((AddedInfo)(o_modi)).getOrnament();//中括弧がとれる le2_set =
	 * (List)((List)le2_tmp).car(); Enumeration e0 = le2_set.elements();
	 * while(e0.hasMoreElements()){ Object modifier = e0.nextElement();
	 * System.out.println("modifier = "+ modifier); String modi =
	 * (((List)modifier).car()).toString(); String modi_val =
	 * (((List)modifier).cdr().car()).toString(); if(modi.equals("limit")){
	 * limit = Integer.valueOf(modi_val).intValue(); System.out.println(limit); }
	 * if(modi.equals("page-limit")){ page_limit =
	 * Integer.valueOf(modi_val).intValue(); } if(modi.equals("separate")){ if(
	 * modi_val.equals("true") ){ separate = true;//
	 * System.out.println(separate); } } if(modi.equals("align")){ page_align =
	 * modi_val; System.out.println(page_align); } if(modi.equals("valign")){
	 * page_valign = modi_val; System.out.println(page_valign); }
	 * if(modi.equals("border")){ linewidth =
	 * Integer.valueOf(modi_val).intValue(); System.out.println(linewidth); }
	 * if(modi.equals("bordercolor")){ linecolor = modi_val;
	 * System.out.println(linecolor); } } }
	 * 
	 * public void get_modifier2(Object o_modi){
	 * 
	 * le2_tmp = ((AddedInfo)o_modi).getOrnament();//中括弧がとれる le2_set =
	 * (List)((List)le2_tmp).car(); Enumeration e0 = le2_set.elements();
	 * while(e0.hasMoreElements()){ Object modifier = e0.nextElement();
	 * System.out.println("modifier = "+ modifier); String modi =
	 * (((List)modifier).car()).toString(); String modi_val =
	 * (((List)modifier).cdr().car()).toString(); if(modi.equals("width")){
	 * width = Integer.valueOf(modi_val).intValue(); System.out.println(width); }
	 * if(modi.equals("height")){ height = Integer.valueOf(modi_val).intValue();
	 * System.out.println(height); } if(modi.equals("align")){ align = modi_val;
	 * System.out.println(align); } if(modi.equals("valign")){ valign =
	 * modi_val; System.out.println(valign); } if(modi.equals("imagepath")){
	 * imagepath = modi_val; System.out.println(imagepath); }
	 * if(modi.equals("fontsize")){ fontsize =
	 * Integer.valueOf(modi_val).intValue(); System.out.println(fontsize); }
	 * if(modi.equals("bgcolor")){ bgcolor = modi_val;
	 * System.out.println(bgcolor); } if(modi.equals("fontcolor")){ fontcolor =
	 * modi_val; System.out.println(fontcolor); } } }
	 */

	
	
	
	
	//文字列の長さを測るメソッド
	public float stringwidth(String s, PDFEnv pdf_env) {
		float s_width;
		//int char_length;

		fontsize = 8;/////////////応急処置10/04
		
		//4.22英語用修正 英語版
		s_width = pdf_env.stringwidth(s, fontsize);
		s_width = (int) s_width;

		//4.22日本語用修正 日本語版
		//char_all = s.length();
		//s_width = char_all * fontsize;

		return s_width;
	}

	
	
	
	
	
	/*
	 * public void set_modifier1( PDFWriter writer ){ if(
	 * !(page_align.equals("null")) ) writer.page_align = page_align; if(
	 * !(page_valign.equals("null")) ) writer.page_valign = page_valign; if(
	 * linewidth == 0 ) writer.line = false; else writer.linewidth = linewidth;
	 * if( !(linecolor.equals("null")) ){ if( linecolor.startsWith("#") ){
	 * String line = linecolor.substring(1);//#を取り除く //直接オブジェクトに各RGB値を代入
	 * 255fはfloat型ってこと writer.line_r = Integer.parseInt(line.substring(0,2),
	 * 16)/255f; writer.line_g = Integer.parseInt(line.substring(2,4), 16)/255f;
	 * writer.line_b = Integer.parseInt(line.substring(4,6), 16)/255f;
	 * writer.line_rgb = true; } //スポットカラーの処理 } }
	 */

	public void set_modifier2(String s, float s_width, PDFEnv pdf_env,
			PDFValue value) {

		fontsize = 8;/////////////応急処置10/04
		
		if (width == 0){
//			value.width = s_width;
		}
		else {
			value.set_width = true;//多分,単に増える分２等分してするのではなく,widthを指定したものは大きさを変えないようにする試みの痕跡
//			value.width = width;
			int tmp_width = width;

			/*
			 * テキストボックス処理
			 */
			if (width < s_width) {
				int char_width = 0;
				int char_num;
				//一行に入る文字数を変数char_numに取得
				for (char_num = 1; char_width < width; char_num++)
					char_width = char_width + fontsize;
				//int char_num = (int)(width/writer.fontsize);
				//int char_all = s.length();
				int begin = 0;
				int end = char_num + 1;
				value.s = s.substring(begin, end);///エラーあり!!
				value.s_width = (char_num - 2) * fontsize;//// <-強引
				char_all = char_all - char_num;
				//最後の一行になるまでこのwhileループで処理
				while (char_all > char_num) {
					//width = width + tmp_width;
					begin = begin + char_num;
					end = end + char_num;
					String tmp_s = s.substring(begin, end);
					System.out.println("tmp_s = " + tmp_s);
					value.text_box.addElement(tmp_s);
					value.row++;
					char_all = char_all - char_num;//全文字数から一行分引いていく
				}
				begin = end;
				String tmp_s = s.substring(begin);
				value.text_box.addElement(tmp_s);
				value.row++;
				value.text_line = new String[value.text_box.size()];
				value.text_box.copyInto(value.text_line);
			}
			System.out.println("11111111111111111 " + s_width);
			System.out.println("33333333333333333 " + value.row);
		}
		if (height == 0 || height < fontsize * value.row){
//			value.height = fontsize * value.row;
		}
		else if (height > fontsize * value.row){
//			value.height = height;
		}

		value.align = align;
		System.out.println(value.align);

		value.valign = valign;
		System.out.println(value.valign);

		value.fontsize = fontsize;

		/*
		  //imageの処理 
		   * if( !(imagepath.equals("null")) ){ 
		   * StringTokenizer token = new StringTokenizer(s, ".", false);
		   * String type = "null";
		   * while(token.hasMoreTokens() ){
		   *   type = token.nextToken();
		   * } 
		   * if(type.equals("jpg") || type.equals("jpeg") || type.equals("gif")){
		   *   if(type.equals("jpg") )
		   *     type = "jpeg";
		   *   if( type.equals("gif") )
		   *     System.out.println("bbbbbbbbbbbb");
		   *   System.out.println("type = "+type);
		   *   StringBuffer path = new StringBuffer();
		   *   path.append(imagepath); path.append(s);
		   *   value.filename = new String(path);
		   *   System.out.println("imagefile's path ="+value.filename);
		   *   value.image_num = p.open_image_file(type,value.filename,"",0);
		   *   System.out.println("ooooooooo"+value.image_num);
		   *   //p.set_parameter("imagewarning", "true");
		   *   if( value.image_num != -1 ){
		   *     System.out.println(value.image_num);
		   *     image_width = p.get_value("imagewidth", value.image_num);
		   *     image_height = p.get_value("imageheight", value.image_num);
		   *     scale_width = (float)width/image_width;
		   *     scale_height = (float)height/image_height;
		   *     if( width != 0 || height != 0 ){
		   *       //System.out.println("%%%"+width+" "+height);
		   *       //System.out.println(image_width+" "+image_height);
		   *       //System.out.println("%%"+scale_width+scale_height);
		   *       if( scale_width ==0 ){
		   *         value.scale = scale_height;
		   *         value.width = (image_width * value.scale);
		   *       }
		   *       else if( scale_height == 0 ){
		   *         value.scale = scale_width;
		   *         value.height = (image_height * value.scale);
		   *       }
		   *       else if(scale_width > scale_height ){
		   *         value.scale = scale_height;
		   *         value.width =(image_width * value.scale);
		   *       }
		   *       else{
		   *         value.scale = scale_width;
		   *         value.height = (image_height * value.scale);
		   *       }
		   *     }
		   *     else{
		   *       value.width = image_width;
		   *       value.height = image_height;
		   *     }
		  
		  //imageのalignを成功させるための処理 value.s_width = value.width;
		  
		  /-test-* image_width = p.get_value("imagewidth",value.image_num);
		  image_height = p.get_value("imageheight",value.image_num);
		  scale_width = (float)width/image_width; scale_height =
		  (float)height/image_width; if( width == 0 && height == 0 ){
		  value.width = image_width; value.height = image_height; } else if(
		  width != 0 ){ value.scale = scale_width; value.height = image_height *
		  value.scale; } else if( height != 0 ){ value.scale = scale_height;
		  value.width = image_width * value.scale; } else if( ) -test-/ }
		  
		  
		  else{//画像が開けなかった時の処理 value.s = "no images"; } } }
		  
		  //背景色の処理 
		   * if(!(bgcolor.equals("null"))){
		   *   if((bgcolor.indexOf("&")) != -1){
		   *     System.out.println("rrrrrrrrrrrrr");
		   *     int bg_index = bgcolor.indexOf("&");
		   *     String bg_first = bgcolor.substring(0,bg_index);
		   *     String bg_second = bgcolor.substring(bg_index+1);
		   *     if( value.alternate % 2 != 0 ){
		   *       //奇数なら
		   *       if( bg_first.startsWith("#") ){
		   *         String bg = bg_first.substring(1);
		   *         //#を取り除く 
		   *         //直接オブジェクトに各RGB値を代入 255fはfloat型ってこと
		   *         value.bg_r = Integer.parseInt(bg.substring(0,2), 16)/255f;
		   *         value.bg_g = Integer.parseInt(bg.substring(2,4), 16)/255f;
		   *         value.bg_b = Integer.parseInt(bg.substring(4,6), 16)/255f;
		   *         value.bg_rgb = true; 
		   *       }
		   *     //スポットカラーの処理 
		   *     } 
		   *     else if( value.alternate % 2 == 0 ){
		   *       //偶数なら 
		   *       if(bg_second.startsWith("#") ){
		   *         String bg = bg_second.substring(1);
		   *         //#を取り除く 
		   *         //直接オブジェクトに各RGB値を代入 255fはfloat型ってこと
		   *         value.bg_r = Integer.parseInt(bg.substring(0,2), 16)/255f;
		   *         value.bg_g = Integer.parseInt(bg.substring(2,4), 16)/255f;
		   *         value.bg_b = Integer.parseInt(bg.substring(4,6), 16)/255f;
		   *         value.bg_rgb = true; 
		   *       }
		   *       //スポットカラーの処理 
		   *     } 
		   *   } 
		   *   //if(!(bgcolor.equals("null"))){
		   *   else{
		   *     if(bgcolor.startsWith("#") ){
		   *       String bg = bgcolor.substring(1);
		   *       //#を取り除く
		   *       //直接オブジェクトに各RGB値を代入 255fはfloat型ってこと 
		   *       value.bg_r = Integer.parseInt(bg.substring(0,2), 16)/255f;
		   *       value.bg_g = Integer.parseInt(bg.substring(2,4), 16)/255f;
		   *       value.bg_b = Integer.parseInt(bg.substring(4,6), 16)/255f;
		   *       value.bg_rgb = true; 
		   *     }
		   *     //スポットカラーの処理 
		   *   } 
		   * }
		  
		  if(!(fontcolor.equals("null"))){
		    if( fontcolor.startsWith("#") ){
		      String font = fontcolor.substring(1);//#を取り除く
		      //直接オブジェクトに各RGB値を代入255fはfloat型ってこと 
		      value.font_r = Integer.parseInt(font.substring(0,2),16)/255f;
		      value.font_g = Integer.parseInt(font.substring(2,4),16)/255f;
		      value.font_b = Integer.parseInt(font.substring(4,6),16)/255f;
		      value.font_rgb = true;
		    } 
		    //スポットカラーの処理
		  }
		}
		 */
	}

}
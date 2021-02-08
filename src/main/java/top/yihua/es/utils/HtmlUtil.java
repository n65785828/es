package top.yihua.es.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import top.yihua.es.product.Goods;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HtmlUtil {

    public static void main(String[] args) throws Exception{
        List<Goods> cup = getData("显卡");

    }

    public static List<Goods> getData(String key) throws IOException {
        List<Goods> goodsList = new ArrayList<>();
        for(int i =1;i<2;i++){
            String url = "https://search.jd.com/Search?keyword="+key+"&enc=utf-8&page="+i;
            Document doc = Jsoup.parse(new URL(url), 3000);
            try{
                Elements elements = doc.getElementById("J_goodsList").getElementsByTag("li");
                elements.stream().forEach(t->{
                    Goods goods = new Goods();
                    Element em = t.getElementsByTag("em").get(1);
                    Elements font = em.getElementsByTag("font");
                    Elements span = em.getElementsByTag("span");
                    String name = em.html();
                    if(font!=null){
                        for (Element f:font) {
                            name=name.replace(f.toString(),"");
                        }
                    }
                    if(span!=null){
                        for (Element s:span) {
                            name=name.replace(s.toString(),"");
                        }
                    }
                    goods.setName(name);
                    goods.setImg(t.getElementsByTag("img").get(0).attr("data-lazy-img"));
                    goods.setPrice(Double.parseDouble(t.getElementsByTag("i").get(0).html()));
                    System.out.println(goods);
                    goodsList.add(goods);
                });
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return goodsList;
    }
}

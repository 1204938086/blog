package com.wyj.blog.utils;

import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

public class MarkdownUtils {
    /**
     * 标准解析，不对标签进行任何修改
     * @param content Markdown内容
     * @return HTML内容
     */
    public static String MarkdownToHtml(String content){
        // 创建解析器
        Parser parser = Parser.builder().build();
        // 解析Markdown
        Node document = parser.parse(content);
        // 创建HTML渲染器
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        // 渲染并返回
        return renderer.render(document);
    }

    /**
     * 对Markdown 转 HTML 的格式进行扩展
     * @param content Markdown 内容
     * @return HTML内容
     */
    public static String MarkdownToHtmlExt(String content){
        // 创建解析器
        Parser parser = Parser.builder()
                //.extensions(tableExtension)
                .build();
        // 解析Markdown
        Node document = parser.parse(content);
        // 创建HTML渲染器
        HtmlRenderer renderer = HtmlRenderer.builder()
                .attributeProviderFactory(context -> new CustomAttributeProvider())
                .build();

        // 渲染并返回
        return renderer.render(document);
    }
    /**
     * 处理标签的属性
     */
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //System.out.println(node.getClass().getName()+","+tagName);
            //改变a标签的target属性为_blank
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }else
            // 表格样式
            if (node instanceof TableBlock) {
                attributes.put("class", "ui celled table");
            }else if (node instanceof Heading) {
                // H1-H5样式
                switch (tagName){
                    case "h1":attributes.put("class", "markdown-h1");break;
                    case "h2":attributes.put("class", "markdown-h2");break;
                    case "h3":attributes.put("class", "markdown-h3");break;
                    case "h4":attributes.put("class", "markdown-h4");break;
                    case "h5":attributes.put("class", "markdown-h5");break;
                }


            }else if (tagName.equals("code")  &&  !(node instanceof FencedCodeBlock)){
                //行内代码样式
                attributes.put("class", "markdown-linecode");
            }else if (tagName.equals("ol") || tagName.equals("ul")){
                attributes.put("class","markdown-list");
            }else if(node instanceof BlockQuote){// 引用 样式
                attributes.put("class","markdown-blockquote");
            }
        }
    }
}

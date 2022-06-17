/**
 * Markdown 渲染器，用于指定要渲染的形式
 */

import {marked} from "marked";
import prismjs from '@/assets/js/prism.js';

let renderer = new marked.Renderer();

renderer.heading = (text, level)=>{
    return '<h' + level + ' class="markdown-head">'+text+'</h' + level + '>';
}

renderer.codespan = (text)=>{
    return "<code class='markdown-linecode'>"+text+"</code>"
}

renderer.blockquote = (text)=>{
    
    return "<blockquote class='markdown-blockquote'>"+text+"</blockquote>"
}

renderer.listitem = (text)=>{
    return "<li class='markdown-list'>"+text+"</li>"
}

renderer.code = (text,language)=>{
    try{
        prismjs.highlight(text,prismjs.languages[language],language)
    }catch(err){
        return "<pre class='codebox'><code class='language-"+language+" line-numbers'>"+prismjs.highlight(text,prismjs.languages.text,"text")+"</code></pre>"
    }
    return "<pre class='codebox'><code class='language-"+language+" line-numbers'>"+prismjs.highlight(text,prismjs.languages[language],language)+"</code></pre>"
}


renderer.table = (th,td)=>{
    return "<table class='markdown-table'>"+th+td+"</table>"
}

renderer.image = (src,_,title,c,d)=>{
    return '<a  href="'+src+'"  data-fancybox="gallery"  data-caption="'+title+'"><image class="markdown-img" src="'+src+'" ></image></a>';
}
export default {renderer};
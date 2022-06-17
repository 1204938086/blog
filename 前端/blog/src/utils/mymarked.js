import { marked } from "marked";
import render from "@/utils/render";
import prismjs from '@/assets/js/prism.js';




marked.setOptions({
  renderer: render,
  gfm: true,
  tables: true,
  breaks: false,
  pedantic: false,
  sanitize: false,
  smartLists: true,
  smartypants: false,

});

/**
 * 处理代码高亮
 */
// marked.setOptions({
//   highlight: function (code,language) {
//     // console.log(prismjs.languages);
//     return prismjs.highlight(code,prismjs.languages[language],language);
//   }
// });

export default marked;
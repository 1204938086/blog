<template>
  <div class="dashboard">
    <div class="leftup">
      <div id="hit" style="width: 100%; height: 100%"></div>
      <div id="visitor" style="width: 100%; height: 100%"></div>
    </div>
    <div class="rightup">
      <div class="count-box">
        <div class="count-item">
          <span class="count-title">文章数量</span
          ><span class="count-content">{{ total.article }}</span>
        </div>
        <div class="count-item">
          <span class="count-title">点击数量</span
          ><span class="count-content">{{ total.hit }}</span>
        </div>
        <div class="count-item">
          <span class="count-title">分类数量</span
          ><span class="count-content">{{ total.types }}</span>
        </div>
        <div class="count-item">
          <span class="count-title">访客数量</span
          ><span class="count-content">{{ total.visitor }}</span>
        </div>
        <div class="count-item">
          <span class="count-title">标签数量</span
          ><span class="count-content">{{ total.tags }}</span>
        </div>
        <div class="count-item">
          <span class="count-title">评论数量</span
          ><span class="count-content">{{ total.comments }}</span>
        </div>
      </div>
    </div>
    <div class="leftdown">
      <div id="calendar" style="width: 100%; height: 100%"></div>
    </div>
    <div class="rightdown"></div>
  </div>
</template>

<script>
// 引入 echarts 核心模块，核心模块提供了 echarts 使用必须要的接口。
import * as echarts from "echarts/core";
// 引入柱状图图表，图表后缀都为 Chart
import { BarChart, HeatmapChart } from "echarts/charts";
// 引入提示框，标题，直角坐标系，数据集，内置数据转换器组件，组件后缀都为 Component
import {
  TitleComponent,
  TooltipComponent,
  GridComponent,
  DatasetComponent,
  TransformComponent,
  VisualMapComponent,
  CalendarComponent,
} from "echarts/components";
// 标签自动布局，全局过渡动画等特性
import { LabelLayout, UniversalTransition } from "echarts/features";
// 引入 Canvas 渲染器，注意引入 CanvasRenderer 或者 SVGRenderer 是必须的一步
import { CanvasRenderer } from "echarts/renderers";

// 注册必须的组件
echarts.use([
  HeatmapChart,
  TitleComponent,
  CalendarComponent,
  VisualMapComponent,
  TooltipComponent,
  GridComponent,
  DatasetComponent,
  TransformComponent,
  BarChart,
  LabelLayout,
  UniversalTransition,
  CanvasRenderer,
]);
import { onUnmounted, reactive } from "@vue/runtime-core";
import request from "@/utils/request";
import moment from "moment";

export default {
  name: "DashBoard",
  setup() {
    //#region ============== 图表数据对象 =============
    let dates = []; // 日期列表
    let dateHit = []; // 日期对应文章点击数
    let dateVisitor = []; // 日期对应访客数量
    let commitData = []; // 提交记录数据
    //#endregion
    //#region ============== 图表对象 ================
    let calendar = null; // 提交记录
    let hit = null; // 点击量
    let visitor = null; // 访问量
    //#endregion
    //#region ============== 图表数据获取 ============
    request.post("/admin/alldate").then((res) => {
      const data = res.data;
      for (let i = 0; i < data.length; i++) {
        dates.push(moment(data[i].date).format("YYYY/MM/DD"));
        dateHit.push(data[i].hit);
        dateVisitor.push(data[i].visitor);

        // 每日点击量数据绘制
        hit = echarts.init(document.getElementById("hit"));
        hit.setOption({
          title: {
            text: "每日文章访问量",
          },
          tooltip: {},
          xAxis: {
            data: dates,
          },
          yAxis: {},
          series: [
            {
              name: "访问量",
              type: "bar",
              data: dateHit,
            },
          ],
        });

        // 每日访问量数据绘制
        visitor = echarts.init(document.getElementById("visitor"));
        visitor.setOption({
          title: {
            text: "每日访客数量",
          },
          tooltip: {},
          xAxis: {
            data: dates,
          },
          yAxis: {},
          series: [
            {
              name: "访客数量",
              type: "bar",
              data: dateVisitor,
            },
          ],
        });
      }
    });

    request.post("/admin/allcommit").then((res) => {
      for (let i = 0; i < res.data.length; i++) {
        commitData.push([
          echarts.format.formatTime("yyyy-MM-dd", res.data[i].date),
          res.data[i].commit,
        ]);
      }
      calendar = echarts.init(document.getElementById("calendar"));
      calendar.setOption({
        title: {
          top: 30,
          left: "center",
          text: "提交记录",
        },
        tooltip: {
          position: "top",
          formatter: function (p) {
            return p.data[0] + ": " + p.data[1] + "次";
          },
        },
        visualMap: {
          min: 1,
          max: 21,
          type: "piecewise",
          orient: "horizontal",
          left: "center",
          top: 65,
        },
        calendar: {
          top: 120,
          left: 30,
          right: 30,
          cellSize: ["auto", 13],
          range: "2022",
          itemStyle: {
            borderWidth: 0.5,
          },
          yearLabel: { show: false },
        },
        series: {
          type: "heatmap",
          coordinateSystem: "calendar",
          data: commitData,
        },
      });
    });
    //#endregion

    //#region ============== 总计数据 ==============
    let total = reactive({});
    request.post("/admin/total").then((res) => {
      let data = res.data;
      total.article = data.article;
      total.hit = data.hit;
      total.visitor = data.visitor;
      total.tags = data.tags;
      total.types = data.types;
      total.commit = data.commit;
      total.comments = data.comments;
    });
    //#endregion

    // 销毁图表
    onUnmounted(() => {
      if (calendar !== "" && calendar !== null && calendar !== undefined) {
        calendar.dispose();
      }
      if (hit !== "" && hit !== null && calendar !== undefined) {
        hit.dispose();
      }
      if (visitor !== "" && visitor !== null && visitor !== undefined) {
        visitor.dispose();
      }
    });
    return { total };
  },
};
</script>

<style scoped>
#hit {
  width: 300px;
  height: 300px;
}
#visitor {
  width: 300px;
  height: 300px;
}
/* 仪表盘主体 */
.dashboard {
  display: flex;
  width: 100%;
  height: 100%;
  /* background-color: #fff; */
  flex-wrap: wrap;
}
/* 左上角盒子 */
.leftup {
  display: flex;
  /* background-color: red; */
  width: 50%;
  height: 50%;
  justify-content: space-around;
  align-items: center;
}
/* 左下角盒子 */
.leftdown {
  display: flex;
  /* background-color: blue; */
  width: 50%;
  height: 50%;
}
/* 右上角盒子 */
.rightup {
  display: flex;
  /* background-color: green; */
  justify-content: center;
  width: 50%;
  height: 50%;
}
/* 右下角盒子 */
.rightdown {
  display: flex;
  /* background-color: gray; */
  width: 50%;
  height: 50%;
}
/* 总统计数据盒子 */
.count-box {
  display: flex;
  flex-wrap: wrap;
}
/* 总统计数据标题 */
.count-title {
  font-size: 1em;
  color: #aba2a2;
  /* font-weight: bold; */
}
/* 总统计数据内容 */
.count-content {
  font-size: 2em;
  margin: 20px;
}
.count-item {
  display: flex;
  flex-flow: column;
  background-color: #fff;
  /* width: 300px; */
  width: 40%;
  height: 100px;
  border-radius: 5px;
  margin: 10px;
  box-shadow: 5px 5px 5px #cfcfcf;
  padding: 10px;
}
</style>
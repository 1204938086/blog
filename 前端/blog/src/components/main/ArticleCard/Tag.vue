<template>
  <div class="articletag">
      <span>{{tagTitle}}</span>
  </div>
</template>

<script>
export default {
    name:"Tag",
    props:["tagTitle"]
}
</script>

<style>
    .articletag{
        font-size: 15px;
        padding: 2px 5px;
        margin: 2px;
        border: 1px solid #e1f3d8;
        background-color: #f0f9eb;
        border-radius: 5px;
        color: #67c23a;
        display: flex;
        align-items: center;
    }
</style>
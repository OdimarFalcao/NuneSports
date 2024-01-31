<template>
  <ProductTable />
</template>

<script>
import ProductTable from "./components/ProductTable.vue";

export default {
  name: "App",
  components: {
    ProductTable,
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

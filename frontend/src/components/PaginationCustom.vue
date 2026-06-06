<template>
  <div class="d-flex justify-content-center mt-5" v-if="totalRows > perPage">
    <BPagination
      v-model="pageCourante"
      :total-rows="totalRows"
      :per-page="perPage"
      pills
      class="pagination-personnalisee"
    ></BPagination>
  </div>
</template>

<script setup>
const pageCourante = defineModel({type: Number, required: true});

defineProps({
  totalRows: {
    type: Number,
    required: true
  },
  perPage: {
    type: Number,
    required: true
  }
});
</script>

<style scoped>
.pagination-personnalisee {
  --bs-pagination-active-bg: #4a2c59;
  --bs-pagination-active-border-color: #4a2c59;
  --bs-pagination-color: #4a2c59;
  --bs-pagination-hover-color: #b8a854;
}
</style>

<template>
    <iframe :src="pageUrl" height="90%" width="100%" style="border:0px">

    </iframe>


</template>

<script>
export default {
    data(){
        return {
            pageUrl:""
        }
    },

    methods:{

    },

    beforeMount:function(){
        this.pageUrl = this.$route.query.pageUrl + "?token=" + localStorage.getItem("token");
    }
}

</script>

<style>

</style>

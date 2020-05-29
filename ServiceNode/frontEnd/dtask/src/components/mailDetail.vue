<template>
	<div>
		<h3>收件详情</h3>
		<p>发件人:{{sender}}</p>
		<p>标题: {{title}}</p>
		<p>内容: {{content}}</p>
		<p>发件时间: {{sendTime}}</p>
		<p>是否紧急: {{isImportant}}</p>
		<label>附件：</label>
		<p v-for="item1 in attachment">{{item1}}</p>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				title:'',
				content:'',
				sendTime:'',
				isImportant:'',
				attachment:[],
				sender:localStorage.getItem("sender"),
			}
		},
		methods:{
			getMailDetail(){
				axios.get('/api/mail/mailDetail', {
				    params: {
				      mailID:localStorage.getItem("chosenMail")
				    },headers:{
					"token":localStorage.getItem("token"),
				}
				  })
				  .then(res=>{
						var response=res.data.data;
						this.sender = response.sender;
						this.receiver = response.receiver;
						this.title = response.title;
						this.content = response.content;
						this.sendTime = response.sendTime;
						if(response.important == true){
							this.isImportant = "是";
						}
						else
						{
							this.isImportant = "否";
						}
						var a = JSON.parse(response.attachment);
						for(var i =0 ;i<a.filenameList.length;i++){
							this.attachment[i]=  window.location.protocol+"//"+window.location.host +"//"+a.filenameList[i];
						}
					})
				  .catch(err=> {
					  
				  });
			}
		},
		beforeMount:function(){
			this.getMailDetail();
		}
		}
</script>

<style>
	
</style>

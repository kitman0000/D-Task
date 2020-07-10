<template>
	<div>
		<h3>发件详情</h3>
		<p>收件人:{{receiver}}</p>
		<p>标题: {{title}}</p>
		<p>内容: </br>{{content}}</p>
		<p>发件时间: {{sendTime}}</p>
		<p>是否紧急: {{isImportant}}</p>
		<label>附件：</label>
		<p v-for="item1 in attachment"><a href="item1">{{item1}}</a></p>
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
				receiver:'',
			}
		},
		methods:{
			getMailDetail(){
				axios.get('/api/mail/mailSentDetail', {
				    params: {
				      mailID:localStorage.getItem("chosenSendedMail")
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
						this.receiver = localStorage.getItem("receiver");
						if(response.important == true){
							this.isImportant = "是";
						}
						else
						{
							this.isImportant = "否";
						}
						var a = response.attachment.split(',');
						for(var i =0 ;i<a.length;i++){
							this.attachment[i]=  window.location.protocol+"//"+window.location.host +"//"+a[i];
						}
					})
				  .catch(err=> {
					  
				  });
			}
		},
		beforeMount:function(){
			this.getMailDetail();
		},
		}
</script>

<style>
	
</style>

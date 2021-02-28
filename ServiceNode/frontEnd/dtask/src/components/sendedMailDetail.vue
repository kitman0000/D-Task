<template>
	<div>
		<h3>收件详情</h3>
		<el-card class="box-card">
			<p>主题: {{title}}</p>			
			<p>来自:{{sender}}</p>
			<el-tag v-if="isImportant" type="danger">紧急邮件</el-tag>
		</el-card>
		<el-card class="box-card" style="min-height:400px" >
			<p>内容: <br>{{content}}</p>
		</el-card>

		<div  v-if='attachment.length !=0'>
			<el-card class="box-card" >
				<label>附件：</label>
				<p v-for="item1 in attachment"><a :href="item1">{{item1}}</a></p>
			</el-card>
		</div>

		<p>发件时间: {{sendTime}}</p>


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
				isImportant:false,
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
						this.sendTime = new Date(response.sendTime).toLocaleDateString().replace(/\//g, '-');
						this.receiver = localStorage.getItem("receiver");
						this.isImportant = response.important;
						console.log(response.attachment.length);
						if(response.attachment.length != 0){
							var a = response.attachment.split(',');
							for(var i =0 ;i<a.length;i++){
								this.attachment[i]=  window.location.protocol+"//"+window.location.host +"//"+a[i];
							}
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

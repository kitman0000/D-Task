<template>
	<div>
	<button @click="test()">1</button>
	<el-tree :data="data4" :props="defaultProps"></el-tree>
	</div>
</template>

<script>
	export default {
		methods: {
			handleClick(row) {
				console.log(row);
			}
		},

		data() {
			return {
				datatest: eval(localStorage.getItem("testData")),
				keyV:{},
				data4: [],
				eNode:[],
				RootNode:{},
				floorKV:[],
			}
		},
		methods:{
			test(){
				for(var i= 0;i<this.datatest.length;i++){
					var newNode;
					if(this.datatest[i].inheritRp.split(':').length == 1){
						newNode = {
							id:this.datatest[i].id,
							label:this.datatest[i].nodeName,
							children:[]
						};
						this.data4.push(newNode)
					}
					else if(this.datatest[i].inheritRp.split(':').length == 2){
						newNode = {
							id:this.datatest[i].id,
							label:this.datatest[i].nodeName,
							children:[]
						};
						this.data4[0].children.push(newNode);
					}
					else if(this.datatest[i].inheritRp.split(':').length == 3){
						newNode = {
							id:this.datatest[i].id,
							label:this.datatest[i].nodeName,
							children:[]
						};
						for(var k =0;k<this.data4[0].children.length+1;k++){
							if(this.data4[0].children[k].id == this.datatest[i].inheritRp.split(':')[1]){
								this.data4[0].children[k].children.push(newNode);
							}
							if(k == this.data4[0].children.length){
								eNode.push(this.datatest[i]);
							}
						}
					}
					else if(this.datatest[i].inheritRp.split(':').length == 4){
						newNode = {
							id:this.datatest[i].id,
							label:this.datatest[i].nodeName,
							children:[]
						};
						for(var k =0;k<this.data4[0].children.length+1;k++){
							for(var m=0;m<this.data4[0].children[k].length+1;m++){
								if(this.data4[0].children[k].id == this.datatest[i].inheritRp.split(':')[1] &&this.data4[0].children[k].children[m].id == this.datatest[i].inheritRp.split(':')[2] ){
									this.data4[0].children[k].children[m].children.push(newNode);
								}
								if(m==this.data4[0].children[k].length){
									this.eNode.push(this.datatest[i]);
								}
							}
							if(k==this.data4[0].children.length){
								this.eNode.push(this.datatest[i]);
							}
							}
						}
					}
					for(var i =0;i<this.eNode.length;i++){
						if(this.eNode[i].inheritRp.split(':').length == 3){
							var newNode = {
								id:this.eNode[i].id,
								label:this.eNode[i].nodeName,
								children:[]
							};
							for(var k =0;k<this.data4[0].children.length+1;k++){
								if(this.data4[0].children[k].id == this.eNode[i].inheritRp.split(':')[1]){
									this.data4[0].children[k].children.push(newNode);
								}
							}
						}
						else if(this.eNode[i].inheritRp.split(':').length == 4){
							newNode = {
								id:this.eNode[i].id,
								label:this.eNode[i].nodeName,
								children:[]
							};
							for(var k =0;k<this.data4[0].children.length+1;k++){
								for(var m=0;m<this.data4[0].children[k].length+1;m++){
									if(this.data4[0].children[k].id == this.eNode[i].inheritRp.split(':')[1] &&this.data4[0].children[k].children[m].id == this.datatest[i].inheritRp.split(':')[2] ){
										this.data4[0].children[k].children[m].children.push(newNode);
									}
									if(m==this.data4[0].children[k].length){
										this.eNode.push(this.eNode[i]);
									}
								}
								if(k==this.data4[0].children.length){
									this.eNode.push(this.eNode[i]);
								}
								}
						}
					}
				}
			}
		}
	
</script>

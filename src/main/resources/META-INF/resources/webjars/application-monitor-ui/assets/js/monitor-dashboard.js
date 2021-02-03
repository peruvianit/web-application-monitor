'use strict';

const dashboard = {}

dashboard.colorThreadMx = function(){
	const colors = [
				{"state": "NEW", "color": "#337AB7"},
				{"state": "RUNNABLE", "color": "#5CB85C"},
				{"state": "BLOCKED", "color": "#D9534F"},
				{"state": "WAITING", "color": "#F0AD4E"},
				{"state": "TIMED_WAITING", "color": "#F0AD4E"},
				{"state": "TERMINATED", "color": "#A3A3A3"},
			];
	
	return colors;
} 

dashboard.colorDaemon = function(){
	const colors = [
				{"state": "DAEMON", "color": "#D9534F"},
				{"state": "NOT_DAEMON", "color": "#5CB85C"},
			];
	
	return colors;
} 

dashboard.loadThreadMx = function(){
	const url = "/web-template-spring-boot-web-2/io-peruvianit/monitor/dashboard/thread-mx/";
	return fetch(url)
		.then(response => response.json())
		.then(data => data)
		.catch(err => console.error(err));
};

dashboard.loadLogs = function(){
	const url = "/web-template-spring-boot-web-2/io-peruvianit/monitor/dashboard/logs/";
	return fetch(url)
		.then(response => response.json())
		.then(data => data)
		.catch(err => console.error(err));
};

dashboard.loadInfoServer = function(){
	const url = "/web-template-spring-boot-web-2/io-peruvianit/monitor/dashboard/info-server/";
	return fetch(url)
		.then(response => response.json())
		.then(data => data)
		.catch(err => console.error(err));
};

dashboard.detectDeadLock = function(){
	const url = "/web-template-spring-boot-web-2/io-peruvianit/monitor/dashboard/thread-mx/detect-deadlock/";
	return fetch(url)
		.then(response => response.json())
		.then(data => data)
		.catch(err => console.error(err));
};

dashboard.loadInfoThread = function(){
	// Not used, info come into dashboard.loadThreadMx()
	const url = "/web-template-spring-boot-web-2/io-peruvianit/monitor/thread-dump-fully/";
	return fetch(url)
		.then(response => response.json())
		.then(data => data)
		.catch(err => console.error(err));
}

dashboard.threadInfo = function(data){
	
	let infoThread = "";
	let countThread = 0;
	
	data.forEach( function (item){
		infoThread += `
			 <div class="col-lg-2 col-md-6">
	            <div class="panel panel-${item.classColor}">
	                <div class="panel-heading">
	                    <div class="row">
	                        <div class="col-xs-3">
	                            <i class="fa fa-star fa-3x"></i>
	                        </div>
	                        <div class="col-xs-9 text-right">
	                            <div class="huge">${item.count}</div>
	                        </div>
	                    </div>
	                    <div class="row">
	                    	<div class="col-xs-12 text-left">
	                        	<div>${item.name}</div>
	                        </div>
	                    </div>
	                </div>
	                <a href="#" class="thread-detail" data-stateThread="${item.name}" data-count="${item.count}">
	                    <div class="panel-footer">
	                        <span class="pull-left">Details</span>
	                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
	                        <div class="clearfix"></div>
	                    </div>
	                </a>
	            </div>
	        </div>
			`;
		countThread+=parseInt(item.count);
	});	
	
	document.getElementById("info-thread").innerHTML = infoThread;
	
	let hrefs = document.getElementsByClassName("thread-detail");
	  for (var i = 0; i < hrefs.length; i++) {
		  hrefs.item(i).addEventListener('click', function(e){
			  e.preventDefault(); /*use if you want to prevent the original link following action*/
			  const stateThread = e.currentTarget.getAttribute("data-stateThread");
			  const count = e.currentTarget.getAttribute("data-count")
			  document.getElementById("title-modal-thread-info").innerHTML = `Thread MX [${stateThread}] : ${count} analyzed`;
			  
			  const threads = renderModalthreadInfo(stateThread);
			  
		  });
	}
	
	document.getElementById("info-server-thread").innerHTML = `numbers of threads analyzed <strong>${countThread}</strong>`;
	dashboard.addSummary('fa-tumblr', + countThread + ' Threads analyzed');
}

dashboard.graficThreadMx = function(data){
	
	let colors = dashboard.colorThreadMx();
	let color_set = []
	data.forEach(function(item){
		let index = colors.map(function(o) { return o.state; }).indexOf(item.label);
		color_set.push(colors[index].color);
	});
	
	Morris.Donut({
        element: 'morris-donut-chart',
        data: data,
        resize: true,
        colors: color_set,
        formatter: function (value) { return (value); }
    });
}

dashboard.graficDaemos = function(data){
	let colors = dashboard.colorDaemon();
	let color_set = []
	data.forEach(function(item){
		let index = colors.map(function(o) { return o.state; }).indexOf(item.label);
		color_set.push(colors[index].color);
	});
	
	Morris.Donut({
        element: 'morris-donut-chart-deamon',
        data: data,
        resize: true,
        colors: color_set,
        formatter: function (value) { return (value); }
    });
}

dashboard.renderLogs = function(data){
	let dataSet = [];
	
	data.forEach(function(item){
		let array = []
		array[0] = item.pathNameFile;
		array[1] = item.size;
		array[2] = item.lastDateModified;
		array[3] = "";
		
			
		dataSet.push(array);
	});
	
	$('#datable-log').DataTable({
		bFilter: true,
		data: dataSet,
		columns: [
		    { title: "Name"},
		    { title: "Size", orderable: false  },
		    { title: "Last modified", orderable: false },
		    {
                "render": function ( data, type, row ) {
                    return '<a href="/web-template-spring-boot-web-2/io-peruvianit/monitor/logs/' + row[0] + '" target="_blank"><i class="fa fa-download fa-fw"></i></a>';
                },
                orderable: false 
            }
		 ]
    });
	
	dashboard.addSummary('fa-file', + data.length + ' Log files read');
}

dashboard.addSummary = function (icon, message){
	let lineSummary = `
		 <div class="list-group-item">
            <i class="fa ${icon} fa-fw"></i> ${message}
            </span>
        </div>
		`;
	
	document.getElementById('info-summary').insertAdjacentHTML('beforeend', lineSummary);
	
}

async function renderInfoServer(){
	console.log('start loading info server');
	const infoServer = await dashboard.loadInfoServer();
	console.log('loaded loading info server');
	
	document.getElementById("info-server-host").innerHTML = `Proccesed of the server <strong>${infoServer.hostname}</strong>, with the ip address <strong>${infoServer.ipAddress}</strong>`;
	console.log(infoServer);
} 

async function renderThreadMx(){
	console.log('start loading ThreadMx');
	const threadMx = await dashboard.loadThreadMx();
	console.log('loaded ThreadMx');
	console.log(threadMx);
	
	console.log('set value snapshotTime');
	document.getElementById("page-header-title").innerHTML = `Thread MX - [ Snapshot: ${threadMx.snapshotTime} ]`;
	
	console.log('render threads info');
	dashboard.threadInfo(threadMx.threads);
	console.log('finish render threads info');
	
	console.log('render graphic Daemons');
	dashboard.graficDaemos(threadMx.daemons);
	console.log('finish render graphic Daemons');
	
	console.log('render graphic Daemons');
	dashboard.graficThreadMx(threadMx.threadMx);
	console.log('finish render graphic Daemons');
	
	console.log('set value threadsInfoFull into object dashboard');
	dashboard.threadsInfoFull = threadMx.threadsInfoFull;
	
} 

async function renderDetectDeadLock(){
	console.log('start loading detect deadlock');
	const detectDeadlock = await dashboard.detectDeadLock();
	
	if (detectDeadlock != undefined && detectDeadlock.length > 0 ){
		document.getElementById('count-thread-deadlock').innerHTML = detectDeadlock.length;
		document.getElementById('message-deadlock').classList.remove('hidden')
		
		document.getElementById("title-modal-thread-info").innerHTML = `${detectDeadlock.length} deadlock`;
		
		document.getElementById('view-detail-deadlock').addEventListener('click', function(e){
			let jsonString = JSON.stringify(detectDeadlock, undefined, 2)
				
			document.getElementById("content-modal-thread-info").innerHTML = jsonString;
			$("#modal-info-thread").modal('show');
		});
	}
		
	console.log('loaded loading detect deadlock');
	
	
	console.log(detectDeadlock);
} 

async function renderLogs(){
	console.log('start loading Logs');
	const logs = await dashboard.loadLogs();
	console.log('loaded Logs');
	console.log(logs);
	
	console.log('render graphic Daemons');
	dashboard.renderLogs(logs);
	console.log('finish render graphic Daemons');
} 

async function renderModalthreadInfo(stateThread){
	console.log('start loading info thread');
	
	const infoThread = dashboard.threadsInfoFull
	const filterThread = infoThread.filter(function(thread) {
		  return thread.stateThread == stateThread;
		});;
	
	let jsonString = JSON.stringify(filterThread, undefined, 2)
	
	document.getElementById("content-modal-thread-info").innerHTML = jsonString.replaceAll(stateThread, `<strong class="bg-primary">${stateThread}</strong>`);
	$("#modal-info-thread").modal('show');
	
	console.log('loaded info thread');
}

renderInfoServer();
renderThreadMx();
renderDetectDeadLock();
renderLogs();

document.getElementById("link-system").addEventListener('click', function(e){
	$("#modal-info-system").modal('show');
});

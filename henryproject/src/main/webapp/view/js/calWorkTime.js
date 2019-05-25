// stats.js r6 - http://github.com/mrdoob/stats.js
var calWorkTime=function(){

	function show(){
		var trList = $("#table1").find("tr");

		var totalhours = 0;
		var addoverhours = 0;

	    for (var i=0;i<trList.length;i++) {
	        var tdArr = trList.eq(i).find("td");


	        var start_h = tdArr.eq(1).find('select').eq(0).find("option:selected").text();
	        var start_m = tdArr.eq(1).find('select').eq(1).find("option:selected").text();
	        var start_s = '00';
	        var starttime = start_h+':'+start_m+':'+start_s;

	        var end_h = tdArr.eq(2).find('select').eq(0).find("option:selected").text();
	        var end_m = tdArr.eq(2).find('select').eq(1).find("option:selected").text();
	        var end_s = '00';
	        var endtime = end_h+':'+end_m+':'+end_s;

	        var workhours_morning = suboftime(starttime,'12:00:00');
	        var workhours_afternoon = suboftime('13:00:00',endtime);

	        if(endtime!= '::00' && endtime>'17:45:00'){
	        	workhours_afternoon = workhours_afternoon - 0.25;
	        }

	        var workhours = plashours(workhours_morning,workhours_afternoon);
	        tdArr.eq(3).find('label').text(workhours);

	        var overhours = suboftime('18:00:00',endtime);
	        tdArr.eq(4).find('label').text(overhours);

	        addoverhours = plashours(addoverhours,overhours);
	        tdArr.eq(5).find('label').text(addoverhours);

	        totalhours = plashours(totalhours,workhours);

	    }
	    $("#kousutable").find("tr").eq(0).find("td").eq(1).find('label').text(totalhours);
	    $("#kousutable").find("tr").eq(1).find("td").eq(1).find('label').text(addoverhours);
	}

	//time2-time1
	function suboftime(time1,time2){
		var d1 = new Date('1990/01/01 '+time1);
        var d2 = new Date('1990/01/01 '+time2);
        var workhours=(d2.getTime() - d1.getTime())%(24*3600*1000);
        workhours = workhours/(3600*1000);
        workhours = workhours.toFixed(2);

        if(isNaN(workhours) || workhours<0){
        	workhours='0.00';
        }
        return workhours;
	}

	//hours1+hours2
	function plashours(hours1,hours2){
		var plashours = Number(hours1) + Number(hours2);
		plashours = plashours.toFixed(2);

        return plashours;
	}

};

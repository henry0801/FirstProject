package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dto.StatisticDto;
import com.form.StatisticForm;
import com.service.StatisticService;

@Controller
@RequestMapping("statistic")
public class StatisticController {

	Logger logger = LoggerFactory.getLogger(StatisticController.class);

	@Autowired
    private StatisticService statisticService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String show(Model model) {
        return refresh(new StatisticForm(), model);
    }

	@RequestMapping(value = "", params = "refresh", method = RequestMethod.POST)
	public String refresh(@ModelAttribute StatisticForm form, Model model) {
		List<StatisticDto> statistics = statisticService.getAllStatistic();
		model.addAttribute("statistics", statistics);

		return "statistic";
	}

    //戻るボタン
    @RequestMapping(value = "", params = "return", method = RequestMethod.POST)
    public String toMenu(@ModelAttribute StatisticForm form, Model model) {

        return "menu";
    }

	@RequestMapping(value = "", params = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute StatisticForm form, Model model) {

		List<StatisticDto> statistics_exist = new ArrayList<>();
		String[] id = form.getId_exist();
		String[] name = form.getName_exist();
		String[] status = form.getStatus_exist();
		String[] biko = form.getBiko_exist();
		String[] delete_flg = form.getDelete_flg_exist();

		List<StatisticDto> statistics_new = new ArrayList<>();
		String[] name_new = form.getName_new();
		String[] status_new = form.getStatus_new();
		String[] biko_new = form.getBiko_new();
		String[] delete_flg_new = form.getDelete_flg_new();

		List<StatisticDto> statistics_delete = new ArrayList<>();

		if (id!=null) {
			for (int i = 0; i < id.length; i++) {

				StatisticDto statisticDto = new StatisticDto();
				statisticDto.setId(id[i]);
				statisticDto.setName(name.length>0?name[i]:"");
				statisticDto.setStatus(status.length>0?status[i]:"");
				statisticDto.setBiko(biko.length>0?biko[i]:"");

				if ("0".equals(delete_flg[i])) {
					statistics_exist.add(statisticDto);
				}else if ("1".equals(delete_flg[i])) {
					statistics_delete.add(statisticDto);
				}
			}
		}

		statisticService.updateStatisticById(statistics_exist);

		statisticService.deleteStatisticById(statistics_delete);

		if (delete_flg_new !=null) {
			for (int i = 0; i < delete_flg_new.length; i++) {

				if ("1".equals(delete_flg_new[i]))
					continue;

				StatisticDto statisticDto = new StatisticDto();

				statisticDto.setName(name_new.length>0?name_new[i]:"");
				statisticDto.setStatus(status_new!=null && status_new.length>0?status_new[i]:"");
				statisticDto.setBiko(biko_new.length>0?biko_new[i]:"");

				statistics_new.add(statisticDto);
			}
		}

		statisticService.insertStatisticById(statistics_new);




		return refresh(form, model);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
    public String[] add(@ModelAttribute StatisticDto statisticDto,Model model) {

		List<StatisticDto> statistics = new ArrayList<>();
		statistics.add(new StatisticDto());

		model.addAttribute("statistics", statistics);


		String[] datas = {"test1", "test2", "test3"};
        return datas;
    }

}
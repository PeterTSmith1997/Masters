package dataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import main.LogData;

/**
 * @author 16018262
 * @version 14 Dec 2019
 */
public class Analise {
	private  Database database;
	/**
	 * Small method to aid in the analysis of URL's by changing all the inputs to lower case
	 * @param str The string to check
	 * @param sub The substring that we are looking for
	 * @return Boolean The comparison between strings
	 */
	private static boolean containIgnoreCase(String str, String sub) {
		return str.toLowerCase().contains(sub.toLowerCase());
	}
	
	/**
	 * Set the risk total weights for the dataBase
	 */
	private Double dbRiskMod = 0.1;

	private Double rawRiskMod = 0.90;

	private Double ASNrisk = 0.0;

	/**
	 *
	 */
	public Analise() {

	}

	/**
	 * @return the dbRiskMod
	 */
	public Double getDbRiskMod() {
		return dbRiskMod;
	}

	/**
	 * sets the map of IP counts
	 * 
	 * @param hits
	 *            - the ArrayList to be sorted
	 * @return The map of sorted IPs with the no. of times in the dataset
	 */
	public HashMap<String, Integer> getIpCounts(ArrayList<Hits> hits) {
		HashMap<String, Integer> countMap = new HashMap<String, Integer>();
		for (int i = 0; i < hits.size(); i++) {
			String key = hits.get(i).getiPaddr();
			if (countMap.containsKey(key)) {
				int count = countMap.get(key);
				count++;
				countMap.put(key, count);
			} else {
				countMap.put(key, 1);
			}
		}
		return countMap;
	}


	/**
	 * sets the map of IP counts
	 * 
	 * @param hits
	 *            - the ArrayList to be sorted
	 * @param mainUi 
	 * @param mainUi 
	 * @return 
	 */
	public void SetRiskmap(ArrayList<Hits> hits, DataStore dataStore, LogData mainUi) {

	}

	/**
	 * Set the map of page count
	 * @param hits - the ArrayList to be sorted
	 * @return The map of page counts
	 */
	public Map<String, Integer> getPageCounts(ArrayList<Hits> hits) {
		Map<String, Integer> countMap = new TreeMap<String, Integer>();
		for (int i = 0; i < hits.size(); i++) {
			String key = hits.get(i).getRequest();
			if (countMap.containsKey(key)) {
				int count = countMap.get(key);
				count++;
				countMap.put(key, count);
			} else {
				countMap.put(key, 1);
			}
		}

		return countMap;
	}

	/**
	 *
	 * @param hits- the ArrayList to be sorted
	 * @return The map of protocol counts
	 */
	public Map<String, Integer> getProtocalCounts(ArrayList<Hits> hits) {
		Map<String, Integer> countMap = new TreeMap<String, Integer>();
		for (int i = 0; i < hits.size(); i++) {
			String key = hits.get(i).getProtocal();
			if (countMap.containsKey(key)) {
				int count = countMap.get(key);
				count++;
				countMap.put(key, count);
			} else {
				countMap.put(key, 1);
			}
		}

		return countMap;
	}

	/**
	 * @return the rawRiskMod
	 */
	public Double getRawRiskMod() {
		return rawRiskMod;
	}

	/**
	 *
	 * @param hits - the ArrayList to be sorted
	 * @return The map of referer counts
	 */
	public Map<String, Integer> getRefererCounts(ArrayList<Hits> hits) {
		Map<String, Integer> countMap = new TreeMap<String, Integer>();
		for (int i = 0; i < hits.size(); i++) {
			String key = hits.get(i).getReferer();
			if (countMap.containsKey(key)) {
				int count = countMap.get(key);
				count++;
				countMap.put(key, count);
			} else {
				countMap.put(key, 1);
			}
		}
		return countMap;
	}

	/**
	 *
	 * @param hits - the ArrayList to be sorted
	 * @return The map of time counts
	 */
	public Map<String, Integer> getTimeCounts(ArrayList<Hits> hits) {
		Map<String, Integer> countMap = new TreeMap<String, Integer>();
		for (int i = 0; i < hits.size(); i++) {
			String dateTime = hits.get(i).getDateTime();
			String[] data = dateTime.split(":");
			String key = data[1] + ":" + data[2];
			if (countMap.containsKey(key)) {
				int count = countMap.get(key);
				count++;
				countMap.put(key, count);
			} else {
				countMap.put(key, 1);
			}
		}
		return countMap;
	}

	/**
	 *
	 * @param hits - the ArrayList to be sorted
	 * @return The total amount of data sent
	 */
	public int getTotalData(ArrayList<Hits> hits) {
		int total = 0;
		for (Hits h : hits) {
			total = total + h.getSize();
		}
		return total;
	}

	/**
	 *
	 * @param hits - the ArrayList of all hits
	 * @param ip The IP to get the data for
	 * @return the total amount of data for an IP
	 */
	public int getTotalDataForIP(ArrayList<Hits> hits, String ip) {
		int total = 0;
		for (Hits h : hits) {
			if (h.getiPaddr().equals(ip)) {
				total = total + h.getSize();
			}
		}
		return total;
	}

	/**
	 *
	 * @param hits - The array of the hits
	 * @return int the total size of the array
	 */
	public int getTotalHits(ArrayList<Hits> hits) {
		return hits.size();
	}

	/**
	 *
	 * @param ip  the IP to calculate the risk for
	 * @param dataStore Contains all the data needed
	 * @param countryCode The countrycode that the IP originates from
	 * @return Double Returns the risk factor
	 */
	public double risk(String ip, DataStore dataStore, String countryCode) {
		double risk = 0;
		Database database = new Database();
		if (!database.knownBots(ip).equals("n/a")) {
			return risk;
		}
		double orrcancesOfipLog = Math
				.log(dataStore.getOrrcancesOfip().get(ip));
		orrcancesOfipLog = orrcancesOfipLog==0.00 ? 00.1 : orrcancesOfipLog;
		double countryRisk = database.countryRisk(countryCode); 
		double responseRisk = 0;
		double requestRisk = 0;
		for (Hits h : dataStore.getHits()) {
			if (h.getiPaddr().equals(ip)) {
//				switch (h.getResponse()) {
//				case 400:
//					responseRisk += 0.5;
//					break;
//				case 401:
//					responseRisk += 5;
//					break;
//				case 403:
//					responseRisk += 2;
//					break;
//				case 404:
//					responseRisk += 1;
//					break;
//				case 429:
//					responseRisk += +2;
//					break;
//				case 500:
//					responseRisk += 0.2;
//					break;
//				case 200:
//					responseRisk -= 1;
//					break;
//				}
				if (dataStore.getReponseScores().get(h.getResponse()) == null) {
					requestRisk =+0;
				}
				else {
					requestRisk += dataStore.getReponseScores().get(h.getResponse()); 
				}
				if (containIgnoreCase(h.getRequest(), "wp-admin")) {
					requestRisk += 3;
				}
				if (containIgnoreCase(h.getRequest(), "login")) {
					requestRisk += 2;
				}
				if (h.getSize() == 0) {
					responseRisk = +6;
				}
			}

		}
		risk = (orrcancesOfipLog * 0.6) + ((requestRisk+responseRisk)*0.3) + (countryRisk * 0.1);
		if (risk > 100) {
			return 100;
		} else if (risk < 1) {
			return 1;
		} else {
			return risk;
		}
	}

	/**
	 * @param dbRiskMod
	 *            the dbRiskMod to set
	 */
	public void setDbRiskMod(Double dbRiskMod) {
		this.dbRiskMod = dbRiskMod;
	}

	/**
	 * @param rawRiskMod
	 *            the rawRiskMod to set
	 */
	public void setRawRiskMod(Double rawRiskMod) {
		this.rawRiskMod = rawRiskMod;
	}
	public int calulateRisk (String ip, DataStore dataStore,String countryCode) {
		database = new Database();
		int totalRisk;
		return totalRisk = (int) ((risk(ip, dataStore, countryCode) * getRawRiskMod())
				+ (database.getRiskIP(ip) * getDbRiskMod()));
	}

}

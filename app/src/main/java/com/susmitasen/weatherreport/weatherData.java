package com.susmitasen.weatherreport;

import org.json.JSONException;
import org.json.JSONObject;

public class weatherData {
    private String mTemperature,mIcon,mCity,mWeatherType,mHumidity,mPressure,mWindspeed,mMintemp,mMaxtemp,mRealfeel,mRain;
    private  int mCondition;
    static weatherData fromJson(JSONObject jsonObject)
    {
        try {
            weatherData weatherD=new weatherData();
            weatherD.mCity=jsonObject.getString("name");
            weatherD.mCondition=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherD.mWeatherType=jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.mIcon=updateWeatherIcon(weatherD.mCondition);
            double tempResult=jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue=(int)Math.rint(tempResult);
            weatherD.mTemperature=Integer.toString(roundedValue);
            weatherD.mHumidity=jsonObject.getJSONObject("main").getString("humidity") ;
            weatherD.mPressure=jsonObject.getJSONObject("main").getString("pressure");
            weatherD.mWindspeed=jsonObject.getJSONObject("wind").getString("speed");
            double tempRes=jsonObject.getJSONObject("main").getDouble("temp_min")-273.15;
            int rValue=(int)Math.rint(tempRes);
            weatherD.mMintemp=Integer.toString(rValue);
            double tempRe=jsonObject.getJSONObject("main").getDouble("temp_max")-273.15;
            int reValue=(int)Math.rint(tempRe);
            weatherD.mMaxtemp=Integer.toString(reValue);
            double tempResu=jsonObject.getJSONObject("main").getDouble("feels_like")-273.15;
            int riValue=(int)Math.rint(tempResu);

            weatherD.mRealfeel=Integer.toString(riValue);

            weatherD.mRain=jsonObject.getJSONObject("clouds").getString("all");
            return weatherD;
        }
         catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String updateWeatherIcon(int condition)
    {
        if (condition>=0&&condition<=300)
        {
            return "storm";
        }
       else if (condition>=300&&condition<=500)
        {
            return "lightrain";
        }
      else  if (condition>=500&&condition<=600)
        {
            return "shower";
        }
       else if (condition>=600&&condition<=700)
        {
            return "snowing";
        }
       else if (condition>=701&&condition<=771)
        {
            return "fog";
        }
       else if (condition>=772&&condition<=774)
        {
            return "stratuscumulus";
        }
       else if (condition>=801&&condition<=802)
        {
            return "cloudy";
        }
        else if (condition>=803&&condition<=804)
        {
            return "stratuscumulus";
        }
        else if (condition>=900&&condition<=902)
        {
            return "storm";
        }
        else if (condition>=905&&condition<=1000)
        {
            return "thun";
        }

        else if (condition==904)
        {
            return "sunny";
        }
        else if (condition==903)
        {
            return "snowing";
        }
        else if(condition==781)
        {
            return "hurricane";
        }

        else if (condition==800)
        {
            return "sunny";
        }
        return "No matches";
    }

    public String getmTemperature() {
        return mTemperature+"°C";
    }

    public String getmIcon() {
        return mIcon;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmWeatherType() {
        return mWeatherType;
    }

    public int getmCondition() {
        return mCondition;
    }

    public String getmHumidity() {
        return mHumidity+"%";
    }

    public String getmPressure() {
        return mPressure+"hPa";
    }

    public String getmWindspeed() {
        return mWindspeed+"m/s";
    }

    public String getmMintemp() {
        return mMintemp+"°C";
    }

    public String getmMaxtemp() {
        return mMaxtemp+"°C";
    }

    public String getmRealfeel() {
        return mRealfeel+"°C";
    }



    public String getmRain() {
        return mRain+"%";
    }
}

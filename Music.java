/*
 Brendan Aucoin
 08/16/17
 this class holds the data for playing sounds and music.
 that will not interfere with other threads you are running at the same time.
 */
package music;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Music 
{
	private Clip clip = null;
	/*the constructor doesnt need to do anything*/
	public Music() 
	{
		
	}
	 /*this method starts a music clip based on the string you pass in. its synchronized so it doesnt screw up with any of the other threads.*/
	  public synchronized void playSound(final String url)
	  {
	    try
	    {
	      if (clip == null)
	      {
	        clip = AudioSystem.getClip();//creating the clip and giving it access to use the sound on your computer
	      }
	      else
	      {
	        clip.close();//if its not null and its started already you wanna close the clip first.
	      }
	      URL viaClass = Music.class.getResource("/music/"+url);//making the url the path to the res folder and the file you want played.
	      AudioInputStream inputStream = AudioSystem.getAudioInputStream(viaClass);//putting the path into a input stream.
	      clip.open(inputStream);//opens the proper clip
	      clip.setFramePosition(0);//putting the song at the beginning
	      clip.start();//starting it.
	    }
	    catch (Exception e)
	    {
	      System.out.println(e.getMessage());
	    }
	  }
	  /*this method starts a music clip based on the string you pass in. its synchronized so it doesnt screw up with any of the other threads.*/
	  public synchronized void playSoundMaxVolume(final String url)
	  {
	    try
	    {
	      if (clip == null)
	      {
	        clip = AudioSystem.getClip();//creating the clip and giving it access to use the sound on your computer
	      }
	      else
	      {
	        clip.close();//if its not null and its started already you wanna close the clip first.
	      }
	      URL viaClass = Music.class.getResource("/music/"+url);//making the url the path to the res folder and the file you want played.
	      AudioInputStream inputStream = AudioSystem.getAudioInputStream(viaClass);//putting the path into a input stream.
	      clip.open(inputStream);//opens the proper clip
	      clip.setFramePosition(0);//putting the song at the beginning
	      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); 
	      float dB = 6.0206f;
	      gainControl.setValue(dB);
	      clip.start();//starting it.
	    }
	    catch (Exception e)
	    {
	      System.out.println(e.getMessage());
	    }
	  }
	  /*playing the sound if it has already started*/
	  public synchronized void startSound()
	  {
	    if(clip!=null){clip.start();}
	  }
	  /*pausing the sound.*/
	  public synchronized void stopSound()
	  {
	    if(clip != null){clip.stop();}
	  }
}

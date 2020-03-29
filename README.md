# icefire-task
My Solution to the icefire prison break task

How I solved it..

My thought process is that, I could get my passcode/id code/access code. Then I could somehow hotwire all the doors of this prison, by adding my access code to each door I pass through as I plot my escape from this place. 

But before this, I was of the opinion that a better way would be to get everyone's access codes and use that to get past their doors while escaping. I then realised that, this is a smart idea but it'll require too much resources and that if anything changes [like if more prisoners came in, then I'd also have to get their passes too].

So, the first thing I did, was to get my own personal accesscode while reading my cardData through the read method in the keyCardParser class. Then, I wrote it down on my left palm so I won't forget it. Then, I made a small device that hotwires each door by adding my passcode to the door's authorized access list. I called this my makeAccessibletoMe method. This method is somewhat automated as it repeatedly makes each door accessible to me through the changeRoomAccess method.

This changeRoomAccess method does the main work, as it gets me proprietary access to the room authorization list which I'd swap with my own updated list that would contain my access code. Then, I'd like my presence to be anonymous, so I removed my name from this updated list as I upload it back to the door.

To prevent aimless journeys, I always make a sign on each door I pass through. Once my device sees this sign, it knows to not enter such a room to reduce my chances of getting caught and be 1 step ahead of the guards trying to stop me.

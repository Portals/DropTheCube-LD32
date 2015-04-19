/*******************************************************************************
 * Copyright 2015 Theodor Angergård
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package se.angergard.game.tween;

import aurelienribon.tweenengine.TweenAccessor;

public class FloatTweener implements TweenAccessor<FloatValue>{

	@Override
	public int getValues(FloatValue target, int tweenType, float[] returnValues) {
		returnValues[0] = target.value;
		return 1;
	}

	@Override
	public void setValues(FloatValue target, int tweenType, float[] newValues) {
		System.out.println(newValues[0]);
		target.value = newValues[0];
	}

}

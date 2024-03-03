package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.strings.COLON
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import com.nasiat_muhib.classmate.ui.theme.SomeStyle

@Composable
fun EditClassDetails(
    classDetails: ClassDetails,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace),
        shape = LargeRounded
    ) {
        Row(
            modifier = Modifier
                .clip(LargeRounded)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = classDetails.weekDay, style = SomeStyle)
            Text(text = classDetails.classroom, style = SomeStyle)
            Text(text = classDetails.section, style = SomeStyle)
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = classDetails.startHour.toString(), style = SomeStyle)
                Text(text = COLON, style = SomeStyle)
                Text(text = classDetails.startMinute.toString(), style = SomeStyle)
                Spacer(modifier = Modifier.width(SmallSpace))
                Text(text = classDetails.startShift, style = SomeStyle)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = classDetails.endHour.toString(), style = SomeStyle)
                Text(text = COLON, style = SomeStyle)
                Text(text = classDetails.endMinute.toString(), style = SomeStyle)
                Spacer(modifier = Modifier.width(SmallSpace))
                Text(text = classDetails.endShift, style = SomeStyle)
            }
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditClassPreview() {
    EditClassDetails(classDetails = ClassDetails("Sun","Gallery 2", "Both", 12, 0, "Pm", 1, 0, "Pm"))
}